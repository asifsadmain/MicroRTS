#!/bin/bash
#SBATCH --account=def-lelis
#SBATCH --time=03-00:00
#SBATCH --job-name=DG_IBR
#SBATCH --cpus-per-task=1
#SBATCH --mem=4G
#SBATCH --output=DG/%x-%j.out
#SBATCH --array=0-10

module load java/17.0.2

javac -cp "lib/*:src" -d bin src/ai/synthesis/LocalSearch/Tests/MainTest.java
java -cp "lib/*:bin" ai.synthesis.LocalSearch.Tests.MainTest 6 0 1
