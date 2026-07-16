package com.example.demo.application;

import com.example.demo.application.port.in.TransferCommand;
import com.example.demo.application.port.in.TransferResult;
import com.example.demo.application.port.in.TransferUseCase;
import com.example.demo.application.port.out.CustomerRepository;
import com.example.demo.application.port.out.WalletRepository;
import com.example.demo.application.port.out.authorization.AuthorizationGateway;
import com.example.demo.application.port.out.authorization.AuthorizeCommand;
import com.example.demo.application.port.out.authorization.AuthorizeResult;
import com.example.demo.application.port.out.notification.NotificationGateway;
import com.example.demo.application.port.out.notification.NotifyCommand;
import com.example.demo.application.port.out.notification.NotifyResult;
import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerType;
import com.example.demo.domain.money.Money;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService implements TransferUseCase {

  private final CustomerRepository customerRepository;
  private final WalletRepository walletRepository;
  private final AuthorizationGateway authorizationGateway;
  private final NotificationGateway notificationGateway;

  @Transactional
  public TransferResult execute(TransferCommand command) {

    final var amount = command.amount();
    if (amount.isZero()) {
      return new TransferResult.Failed("amount should be greather than 0");
    }

    final var payerId = command.payerId();
    final var payeeId = command.payeeId();
    if (payerId.equals(payeeId)) {
      return new TransferResult.Failed("self transfer");
    }

    final var payerCustomer = customerRepository.findById(payerId).orElseThrow();
    if (CustomerType.MERCHANT.equals(payerCustomer.getType())) {
      return new TransferResult.Failed("lojista não pode transferir");
    }

    final var payerWallet = walletRepository.findByCustomerId(payerId).orElseThrow();
    if (payerWallet.getBalance().isLessThanOrEqual(amount)) {
      return new TransferResult.Failed("saldo insuficiente");
    }

    final var authorizationResult = authorizationGateway.authorize(new AuthorizeCommand(payerId, payeeId, amount));
    switch (authorizationResult) {
      case AuthorizeResult.Authorized ignored -> {
      }
      case AuthorizeResult.Unauthorized ignored -> {
        return new TransferResult.Failed("transferência não autorizada");
      }
      case AuthorizeResult.Failed failed -> {
        return new TransferResult.Failed("serviço autorizador indisponível: " + failed.reason());
      }
    }

    final var updatedPayerWallet = payerWallet.withBalance(payerWallet.getBalance().subtract(amount));
    walletRepository.save(updatedPayerWallet);

    final var payeeWallet = walletRepository.findByCustomerId(payeeId).orElseThrow();
    final var updatedPayeeWallet = payeeWallet.withBalance(payeeWallet.getBalance().add(amount));
    walletRepository.save(updatedPayeeWallet);

    final var payeeCustomer = customerRepository.findById(payeeId).orElseThrow();
    notifyCustomers(payerCustomer, payeeCustomer, amount);

    return new TransferResult.Ok();
  }

  private void notifyCustomers(Customer payer, Customer payee, Money amount) {
    final var msgPayer = "Você enviou uma transferência de R$ " + BigDecimal.valueOf(amount.getAmountInCents(), 2);
    final var msgPayee = "Você recebeu uma transferência de R$ " + BigDecimal.valueOf(amount.getAmountInCents(), 2);
    try {
      final var resultPayer = notificationGateway.notify(new NotifyCommand(payer.getEmail(), msgPayer));
      switch (resultPayer) {
        case NotifyResult.Ok ignored -> {
        }
        case NotifyResult.Failed failed ->
            log.warn("falha ao notificar pagador {}: {}", payer.getId(), failed.reason());
      }

      final var resultPayee = notificationGateway.notify(new NotifyCommand(payee.getEmail(), msgPayee));
      switch (resultPayee) {
        case NotifyResult.Ok ignored -> {
        }
        case NotifyResult.Failed failed ->
            log.warn("falha ao notificar recebedor {}: {}", payee.getId(), failed.reason());
      }
    } catch (RuntimeException e) {
      log.warn("falha ao notificar transferencia {}", e.getMessage());
    }
  }

}
