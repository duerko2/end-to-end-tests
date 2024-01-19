# System Tests
The System Tests are responsible for building and testing all the microservices and their respected endpoints in a unified docker network.

## Requirements
To run the service locally, ensure that you have the proper dependencies installed
* Maven
* Docker Compose
* The microservices in the DTUPay system
* * [account-service](https://github.com/duerko2/account-service)
  * [bank-service](https://github.com/duerko2/bank-service)
  * [payment-ervice](https://github.com/duerko2/payment-service)
  * [reporting-service](https://github.com/duerko2/reporting-service)
  * [token-service](https://github.com/duerko2/token-service)

## Build And Run Locally
Ensure that you have downloaded all the required microservices stated in the Requirements.
Assuming that you have followed the required directory structure as stated in Notes, run the following commands to build, run and test the microservices in the system-test.

```Bash
git clone https://github.com/duerko2/system-test
```
```Bash
cd system-test
```
```Bash
./local-build-and-run.sh
```

## Notes
To run the microservice together with the other services in DTUPay, it is required that you follow the directory structure as followed

```Bash
DTUPay
├── account-service 
├── bank-service
├── payment-service
├── reporting-service
├── token-service
└── system-test
```


### Troubleshooting
If you cannot run the build.sh script, ensure that the script is runnable by running the following command (assuming you are in the payment-service directory)
```Bash
sudo chmod +x local-build-and-run.sh
```
