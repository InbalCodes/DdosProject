#!/bin/bash

client_id=$1
echo "Starting client $client_id"

while true; do
	response_code=$(curl -I "http://localhost:8080/ddos?clientId=$client_id" 2>/dev/null | grep HTTP | awk '{print $2}')
	current_time=`date +"%Y-%m-%d"`-`date +"%T"`
	
	echo "[$current_time] $response_code (client_id: $client_id)"
	sleep $(( ( RANDOM % 10 )  + 1 ))
done
