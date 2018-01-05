# APIs

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
Reponse:
{
    "userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
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
Reponse:
{
    "userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
	"username" : "Alice",
	"about" : "I love playing the piano and travelling."
}

**Failure**
Status: BAD_REQUEST - 400
Response: "Invalid credentials."


## Create Post

POST openchat/user/<userId>/posts
{
	"text" : "Hello everyone. I'm Alice."
}
**Success**
Status CREATED - 201
{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hello everyone. I'm Alice.",
 	"date" : "10/01/2018",
	"time" : "11:30:00"
}

**Failure**
Status: BAD_REQUEST - 400

## Retrive Posts (User timeline)

GET - openchat/user/<userId>/timeline
[{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"date" : "10/01/2018",
	"time" : "11:30:00"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hello everyone. I'm Alice.",
 	"date" : "10/01/2018",
	"time" : "09:00:00"
}]

**Success**
Status OK - 200

**Failure**
Status: BAD_REQUEST - 400 (in case user does not exist)

## Follow User

POST - openchat/follow
{
	followerId: Alice ID,
	followeeId: Bob ID
}

**Success**
Status OK - 200

**Failure**
Status: BAD_REQUEST - 400  (in case one of the users doesn't exist)

## Retrieve Wall

GET - openchat/user/<userId>/wall
[{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "BOB_IDxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Planning to eat something with Charlie. Wanna join us?",
 	"date" : "10/01/2018",
	"time" : "13:25:00"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "ALICE_ID-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"date" : "10/01/2018",
	"time" : "11:30:00"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "BOB_IDxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "What's up everyone?",
 	"date" : "10/01/2018",
	"time" : "11:20:50"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "CHARLIE_IDxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Hi all. Charlie here.",
 	"date" : "10/01/2018",
	"time" : "09:15:34"
},{
	"postId" : "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"userId" : "ALICE_ID-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
	"text" : "Anything interesting happening tonight?",
 	"date" : "10/01/2018",
	"time" : "09:00:00"
}]

**Success**
Status OK - 200

**Failure**
Status: BAD_REQUEST - 400  (in case user does not exist)

## Retrieve All Users

GET - openchat/users
[{
	"userId" : "123e4567-e89b-12d3-a456-426655440000",
	"username" : "Alice",
	"about" : "I love playing the pianno and travel.",
},{
	"userId" : "093f2342-e89b-12d3-a456-426655440000",
	"username" : "Bob",
	"about" : "Writer and photographer. Passionate about food and languages."
	"follows" : "false"
},{
	"userId" : "316h3543-e89b-12d3-a456-426655440000",
	"username" : "Charlie",
	"about" : "I'm a basketball player, love cycling and meeting new people. "
	"follows" : "true"
}]

**Success**
Status OK - 200
