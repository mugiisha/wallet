# Wallet API
This an api that helps you to
manage your wallet. You can create a budget and add different accounts, 
deposit money, withdraw money
and check your balance and your budget progress.

- API LINK: [Wallet API](https://wallet-g3d9.onrender.com) 
- DOCUMENTATION LINK: [Wallet API Documentation](https://wallet-g3d9.onrender.com/swagger-ui/index.html)

## Installation
1. Clone the repository
2. Install the dependencies
```bash
mvn install
```
4. Add environment variables
```bash
 DB_URL=
DB_USER=
DB_PASSWORD=
JWT_SECRET=
JWT_EXPIRATION=
```
3. Run the application
```bash
mvn spring-boot:run
```
## Usage
Below is some of the enums used in the api
```java
public enum AccountType {
    BANK_ACCOUNT,
    CASH,
    MOBILE_MONEY,
    OTHER
}
```
```java
public enum Categories {
    RENT,
    EQUIPMENT,
    FOOD,
    TRANSPORT,
    OTHERS
}
```
```java
public enum TransactionType {
    INFLOW,
    OUTFLOW
}
```

## Notice
Once a user try to exceed budget he is prompted with a message that a transaction will exceed
his/her budget and he/she will be asked to update budget or re-do the transaction to confirm exceeding budget.