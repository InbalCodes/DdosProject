# DdosProject
a simple HTTP Denial-of-Service protection system

Requierments:
1. Client

    1.1 The user enters the number of HTTP Clients to simulate.

    1.2 For each HTTP client you will run a separate simultaneous thread/task which will do the following in a loop:

		1.2.1 Send HTTP request to a server with simulated HTTP client identifier as a query parameter (e.g. http://localhost:8080/?clientId=3).

		1.2.2. Wait some random time and then send another request.

		1.2.3 The client will run until key press after which it will gracefully drain all the threads/tasks (wait for all of them to complete) and will exit.

2. Server

    2.1 Starts listening for incoming HTTP requests.

    2.2 For each incoming HTTP request you will do the following:

        2.2.1 Handle the request in a separate thread/task.

        2.2.2 Check if this specific client reached the max number of requests per time frame threshold (no more than 5 requests per 5 secs).

        2.2.3 If the client hasn’t reached the threshold, it will get “200 OK” response otherwise “503 Service Unavailable”.

        2.2.4 Note: The time frame starts on each client’s first request and ends 5 seconds later.

        2.2.5 After the time frame has ended, the client’s first request will open a new time frame and so forth.

    2.3 The server will run until key press after which it will gracefully drain all the threads/tasks and will exit.


To run:

1. Run the server as gradle
2. Run run-simulator.sh with the number of clients you want (default 3)
