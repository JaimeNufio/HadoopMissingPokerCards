import random

maxSkip = 15

with open("cards.txt",'w') as file:
    ranks = range(1,14)
    suits = ['diamonds','spades','clubs','hearts']
    skip = 0

    for suit in suits:
        for rank in ranks:
            if (random.random() > .75 and skip<maxSkip):
                print("skipping:",suit,rank)
                skip+=1
            else:
                file.write("{},{}\n".format(suit,rank))
