import {check} from 'k6';
import http from 'k6/http';

const host = 'localhost';
const port = '8080';
const endpoint = '/v1/transfer'

export default function () {
    let payload = JSON.stringify(
        {
            "payer": 4,
            "payee": 2,
            "amount": 1
        }
    );

    let params = {headers: {"Content-Type": "application/json"}};
    let res = http.post(`http://${host}:${port}${endpoint}`, payload, params);

    check(res, {'is status 200': (r) => r.status === 200});
}