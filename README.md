# **Fuel users microservice**

# Actions (API)
This section shows a list of possible requests and their example responses.
### 1. Registration
**HTTP method**
```
POST
```
**URL**
```
http://DOMAIN_NAME/api/v1/security/register
```
**Body**
```
{
    "email": "test@gmail.com",
    "password": "passwordABC",
    "confirmPassword": "passwordABC"
}
```
**Validation on back-end**

1) Email validation
2) Password == ConfirmPassword
3) Password (ConfirmPassword) length must be > 6 characters

**Response example:**

On error (one of the bad request types):
```jsonc
{
    "success": false,
    "message": "Password and confirm password are not equal: passwordABC != password2"
}
```
On success:
```jsonc
{
    "success": true,
    "message": "You have been successfully registered"
}
```

### 2. Login
**HTTP method**
```
POST
```
**URL**
```
http://DOMAIN_NAME/api/v1/security/login
```
**Body**
```
{
    "email": "test@gmail.com",
    "password": "passwordABC"
}
```
**Response example:**

On error (one of the bad request types):
```jsonc
{
    "success": false,
    "message": "User with email testgmail.com does not exist."
}
```
On success:
```jsonc
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY1MjIxMzc4MSwiZXhwIjoxNjUyNTcyODAwfQ.VIlTrW12ceo4zZ0iDvaPjILl8bminXeZumwoJlpSOXsfaK5Ikhw31zOhoH3kA6Obtg7hZBwNWkWRRxuFuHh9Pg"
}
```

### 3. Update token 
**About this end-point**

If a user has already logged in to the application, the JSON web token the user gets has a limited expiration time (5 days). To refresh the token (in order to prolongate the period of the token validness without the user explicitly logging in) use this end-point. It returns a new JWT based on the token passed through the Header parameters. This token has an updated expiration time (the period of its validity). Be aware to use the new JWT in future requests.

**HTTP method**
```
POST
```
**URL**
```
http://DOMAIN_NAME/api/v1/security/update-token
```
**Security**

Add the following header to the request
```
Authorization: Bearer <json web token>
```
**Response example:**

```jsonc
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY1MjIxMzc4MSwiZXhwIjoxNjUyNTcyODAwfQ.VIlTrW12ceo4zZ0iDvaPjILl8bminXeZumwoJlpSOXsfaK5Ikhw31zOhoH3kA6Obtg7hZBwNWkWRRxuFuHh9Pg"
}
```
