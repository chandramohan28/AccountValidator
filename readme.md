#**Account Validator Application.**
* Account Validator Application expects an object of Account model
* Account model to contain an account number and 1 or more providers name to validate the account.
* The provider URL are configured against each provider name in property file.
* In case of Account provider is empty or null, a business exception will be thrown with status code 460.
* In case of Account number is empty or null, a business exception will be thrown with status code 461.
* Upon successful request, the account number is passed to each providers.
* In case provider not available, a technical exception is thrown with status code 599.
* On its availability, Provider will respond whether the account number is valid or not.
* A list of result entity containing the provider name and is valid is passed as a response to
the initial request made to the Account Validator Application.
