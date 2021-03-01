# Hadoop Solution: Missing Poker Card

For my reference for a quick start-up. I'm sure it'll be useful to someone else eventually lol

## Creating Random Set of Missing Cards:

Run ```$ python3 Input/createCards.py```

## Prepping Hadoop file system:

Make sure HADOOP_CLASSPATH is defined.

```$ export HADOOP_CLASSPATH=$(hadoop classpath)```

Make file system on hadoop.

```$ hdfs dfs -mkdir /user```

```$ hdfs dfs -mkdir /user/jaime```

```$ hadoop fs -mkdir /user/jaime/poker /user/jaime/poker/input```

Move input file into hadoop.

```$ hadoop fs -put '/home/jaime/Desktop/Poker/Input/cards.txt'  /user/jaime/poker/input```

Don't make an output folder. Hadoop will make one.

in sbin, run:

 ```$ start-all.sh```


## Compile and Run:


Compile .java file.

```$ javac -classpath ${HADOOP_CLASSPATH} -d ./Classes/ Poker.java```

Convert to .jar.

```jar -cvf poker.jar -C Classes/ .```

Run with Hadoop.

```$ hadoop jar poker.jar Poker /user/jaime/poker/input /user/jaime/poker/output```

Get Results:

```$ hdfs dfs -cat /user/jaime/poker/output/*```