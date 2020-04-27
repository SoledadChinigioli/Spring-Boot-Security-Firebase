# Spring-Boot-Security-Firebase
Secure API with the integration of firebase.

## Use
You need a simple client integration with firebase. In the frontend or another backend you need to log in your users and get the firebase JWT

### GET API TOKEN

For get the API TOKEN you need to call the apirest with the next url and body:

URL:
``` url
POST http://localhost:8080/token
```
Body:
``` json
{
	"token": "FIREBASE_JWT_UID"
}
```

### GET ALL USERS REGISTER
For get all users register in your firebase app you need to call the apirest with the next url and headers:

URL:
``` url
POST http://localhost:8080/users
```
Body: EMPTY
Headers:
``` json
{
  "Authorization": "API_TOKEN"
}
```

## Metodology
All the calls to the api (except /token) needs the follow header:
Headers:
``` json
{
  "Authorization": "API_TOKEN"
}
```
If you not send the header the server response you with:

Body:
``` json
{}
```
Status 403 - Forbidden to representate the denied access.

## Compatibility
This backend auth found with any login method of firebase, like:
- Email and password.
- Google.
- Facebook.
- And another social media.

