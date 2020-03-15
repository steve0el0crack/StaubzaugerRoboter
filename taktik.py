import random
import sys
import os
import time

x = int(sys.argv[1])
y = int(sys.argv[2])
place_num = [x, y]

world = []
for reihe in range(0, place_num[0]):
        world.append([])
        for place in range(0, place_num[1]):
                world[reihe].append({":x": place, ":y": reihe, ":state" : "", ":distance" : "", ":display" :  ""})	#WORLD
			
def setorigin():
	ocoords = {":x" : random.choice(range(x)), ":y" : random.choice(range(y))}
	world[ocoords[":y"]][ocoords[":x"]][":state"] = "X"
	world[ocoords[":y"]][ocoords[":x"]][":distance"] = 0
	return ocoords
ocoords = setorigin()

def setdisplay():
	for yi, reihe in enumerate(world):								#DISPLAY ----> RENDERING
                for xi, place in enumerate(reihe):
			if  place[":distance"] != "":
				place[":display"] = place[":distance"]					#NORMAL PLACE ------> DISTANCE   
			if  place[":distance"] == 0:
				place[":display"] = place[":state"]						#ORIGIN -----> X 		


def countplacesby(key, value):										#COUNT by {key : value}
	counter = 0
	for y in range(0, place_num[1]):
                for x in range(0, place_num[0]):
			if  world[y][x][key] == value:
        			counter += 1                     
	return counter 	


def presentworld():
	for y in range(0, place_num[1]):
        	for x in range(0, place_num[0]):
			todisplay = str(world[y][x][":display"])
			sys.stdout.write(todisplay.ljust(4))
		print ""

def searchplace(xkey, ykey, xval, yval):
	for reihe in world:
		for place in reihe:
			if place[xkey] == xval and place[ykey] == yval:
				  return place 
def setindex(coords, value): 
	world[coords[1]][coords[0]][":distance"] = value
	
def searchinworld(place):											#Search by STRUCT, return COORDS
	for y in range(0, place_num[0]):
		for x in range(0, place_num[1]):
			if place == world[y][x]:
				return x, y	

def searchbydistance(value):												#Return STRUCT
	for reihe in world:
                for place in reihe:
                        if place[":distance"] == value:
                                  return place    



def numbering(origins, maxdistance):
	recurcoords = []
	for origin in origins:
		x = origin[0]
		y = origin[1]
		for i in [1, -1]:
			coords = searchinworld(searchplace(":x", ":y", x + i, y))
			if coords != None and searchplace(":x", ":y", x + i, y)[":distance"] ==  "":
				setindex(coords, maxdistance)
				recurcoords.append(coords)		
		for i in [1, -1]:
			coords = searchinworld(searchplace(":x", ":y", x, y + i))
			if coords != None and searchplace(":x", ":y", x, y + i)[":distance"] ==  "":
				setindex(coords, maxdistance)	
				recurcoords.append(coords)	
	setdisplay()												#SET WHAT TO RENDER
	presentworld()												#RENDER
	time.sleep(0.2)
	os.system("clear")
	if countplacesby(":distance", "") > 0:
		return numbering(recurcoords, maxdistance + 1) 
	else:
		return maxdistance, recurcoords
print numbering([searchinworld(searchbydistance(0))], 1)

presentworld()








