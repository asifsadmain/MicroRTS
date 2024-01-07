#!/bin/bash
#SBATCH --account=def-lelis
#SBATCH --time=04-00:00
#SBATCH --job-name=BW_FP
#SBATCH --cpus-per-task=1
#SBATCH --mem=4G
#SBATCH --output=BW/%x-%j.out
#SBATCH --array=0-10

module load java/17.0.2

javac -cp "lib/*:src" -d bin src/ai/synthesis/LocalSearch/Tests/MainTest.java
java -cp "lib/*:bin" ai.synthesis.LocalSearch.Tests.MainTest 7 1 1
