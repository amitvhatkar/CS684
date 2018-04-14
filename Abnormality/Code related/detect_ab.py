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
data = json.load(open('realheart-26433-export(6).json'))
print(len(data["ECGReading"]["asvhatkar"]))

X = list()
i = 0;
for record in data["ECGReading"]["asvhatkar"]:
	
	if record != None:
		X.append(int(record["value"]))	

	if(i == 2000):
		X = list(set(X))
		X.sort()
		print("\n-------------------------------\n",X,"\n-------------------------------\n")
		# line 1 points
		Y = list(range(0, len(X)))
		# plotting the line 1 points 
		#fig, ax = plt.subplots()
		#ax.plot(X, Y, label = "line 1")
		#plt.xlabel("Vlotage in mV")
		#plt.title("Values Present in ECG Signal")
		#ax.set_yticks([])
		#plt.plot(Y, Y, label = "line 1")
		bins = range(0, 1100, 50)
		plt.hist(X, bins)
		plt.show()
		print(sum(X)/i)
		X = []
		Y = []
		i = 0
	i = i +1;
