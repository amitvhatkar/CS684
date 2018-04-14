import matplotlib.pyplot as plt
import numpy as np

#import plotly.plotly as py
import json
from pprint import pprint
import sys
#import matplotlib.pyplot as plt
sys.stdout = open('temp', 'w')

#realheart-26433-export(6).json
#realheart-26433-export.json
data = json.load(open('big_file.json'))
print(len(data["ECGReading"]["asvhatkar"]))

X = list()
i = 0;

for record in data["ECGReading"]["asvhatkar"]:
	#print(record["value"].split(','))
	X.extend(record["value"].split(','))
	i= i +1


X = list(map(int, X))
print(X)
bins = range(0, 1100, 60)
plt.hist(X, bins)
#X = list(set(X))
#X.sort()
print("\n-------------------------------\n",len(X),"\n-------------------------------\n")
# line 1 points
Y = list(range(0, 5000))
# plotting the line 1 points 
fig, ax = plt.subplots()
ax.plot(X[10000:15000], Y, label = "line 1")
plt.xlabel("Vlotage in mV")
plt.title("Values Present in ECG Signal")
ax.set_yticks([])
#plt.plot(Y, Y, label = "line 1")
		
plt.show()
	
	

print(sum(X)/len(X))
