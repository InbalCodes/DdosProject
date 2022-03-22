#!/bin/bash

num_of_clients=$1
all_pids=()

if [ -z "$num_of_clients" ]; then
	num_of_clients="3"
fi

echo "About to run $num_of_clients simulators"

for ((i=1;i<=$num_of_clients;i++)); do 
	./run-single-client.sh $i &
	echo "running"
	all_pids+=($!)
done

printf '%s\n' "${all_pids[@]}"
read -n 1 -s -r -p "Press any key to stop the simulation.."

echo "Killing all simulators"

for child_pid in "${all_pids[@]}"; do
	echo "  Killing $child_pid"
	kill -9 "$child_pid"
done