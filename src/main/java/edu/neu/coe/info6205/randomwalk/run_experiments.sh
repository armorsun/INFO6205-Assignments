#!/usr/bin/env bash

for steps in $(seq 1 100); do java RandomWalk.java $steps 1000 | tee -a results.csv; done
