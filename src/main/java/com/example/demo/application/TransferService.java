package com.example.demo.application;

import com.example.demo.application.port.in.TransferCommand;
import com.example.demo.application.port.in.TransferResult;
import com.example.demo.application.port.in.TransferUseCase;
import com.example.demo.application.port.out.CustomerRepository;
import com.example.demo.application.port.out.WalletRepository;
import com.example.demo.domain.CustomerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService implements TransferUseCase {

  private final CustomerRepository customerRepository;
  private final WalletRepository walletRepository;

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

    //TODO: authorizar

    final var updatedPayerWallet = payerWallet.withBalance(payerWallet.getBalance().subtract(amount));
    walletRepository.save(updatedPayerWallet);

    final var payeeWallet = walletRepository.findByCustomerId(payeeId).orElseThrow();
    final var updatedPayeeWallet = payeeWallet.withBalance(payeeWallet.getBalance().add(amount));
    walletRepository.save(updatedPayeeWallet);

    //TODO: notificar

    return new TransferResult.Ok();
  }

}
