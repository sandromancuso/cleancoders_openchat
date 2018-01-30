# APIs

**Running the app: mvn build exec:java**

Here is a list of the APIs that need to be implemented by OpenChat.

## Register New User

POST - openchat/registration
{
	"username" : "Alice",
	"password" : "alki324d",
	"about" : "I love playing the piano and travelling."
}

**Success**
Status CREATED - 201
Response:
{
    "id" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
	"username" : "Alice",
	"about" : "I love playing the piano and travelling."
}

**Failure**
Status: BAD_REQUEST - 400
Response: "Username already in use."

## Login

POST - openchat/login
{
	"username" : "Alice"
	"password" : "alki324d"
}

**Success**
Status OK - 200
Response:
{
    "id" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
	"username" : "Alice",
	"about" : "I love playing the piano and travelling."
}

**Failure**
Status: BAD_REQUEST - 400
Response: "Invalid credentials."


## Create Post

    POST openchat/user/<id>/posts
{
	"text" : "Hello everyone. I'm Alice."
}
**Success**
Status CREATED - 201
{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hello everyone. I'm Alice.",
 	"dateTime" : "2018-01-10T11:30:00Z"
}

**Failure**
Status: BAD_REQUEST - 400 (in case user does not exist)
Response: "User does not exit."

## Retrieve Posts (User timeline)

GET - openchat/user/<id>/timeline
[{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"dateTime" : "2018-01-10T11:30:00Z"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hello everyone. I'm Alice.",
 	"dateTime" : "2018-01-10T09:00:00Z"
}]

**Success**
Status OK - 200

**Failure**
Status: BAD_REQUEST - 400 (in case user does not exist)
Response: "User does not exit."

## Follow User

POST - openchat/follow
{
	followerId: Alice ID,
	followeeId: Bob ID
}

**Success**
Status OK - 201

**Failure**
Status: BAD_REQUEST - 400  (in case one of the users doesn't exist)
Response: "At least one of the users does not exit."

## Retrieve Wall

GET - openchat/user/<id>/wall
[{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "BOB_IDxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Planning to eat something with Charlie. Wanna join us?",
 	"dateTime" : "2018-01-10T13:25:00Z"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "ALICE_ID-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"dateTime" : "2018-01-10T11:30:00Z"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "BOB_IDxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "What's up everyone?",
 	"dateTime" : "2018-01-10T11:20:00Z"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "CHARLIE_IDxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hi all. Charlie here.",
 	"dateTime" : "2018-01-10T09:15:34Z"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "ALICE_ID-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"dateTime" : "2018-01-10T09:00:00Z"
}]

**Success**
Status OK - 200

**Failure**
Status: BAD_REQUEST - 400 (in case user does not exist)
Response: "User does not exist."

## Retrieve All Users

GET - openchat/users
[{
	"id" : "123e4567-e89b-12d3-a456-426655440000",
	"username" : "Alice",
	"about" : "I love playing the pianno and travel.",
},{
	"id" : "093f2342-e89b-12d3-a456-426655440000",
	"username" : "Bob",
	"about" : "Writer and photographer. Passionate about food and languages."
},{
	"id" : "316h3543-e89b-12d3-a456-426655440000",
	"username" : "Charlie",
	"about" : "I'm a basketball player, love cycling and meeting new people. "
}]

**Success**
Status OK - 200

## Retrieve all users followed by another user (followees)

GET - openchat/user/:id/followees
[{
	"id" : "123e4567-e89b-12d3-a456-426655440000",
	"username" : "Alice",
	"about" : "I love playing the pianno and travel.",
},{
	"id" : "093f2342-e89b-12d3-a456-426655440000",
	"username" : "Bob",
	"about" : "Writer and photographer. Passionate about food and languages."
},{
	"id" : "316h3543-e89b-12d3-a456-426655440000",
	"username" : "Charlie",
	"about" : "I'm a basketball player, love cycling and meeting new people. "
}]

**Success**
Status OK - 200
