import random
import sys

x = int(sys.argv[1])
y = int(sys.argv[2])
place_num = [x, y]

world = []
for reihe in range(0, place_num[0]):
        world.append([])
        for place in range(0, place_num[1]):
                world[reihe].append({":x": place, ":y": reihe, ":H" : 0, ":index" : "", ":display" :  "-"})
		
world[random.randint(0, 3) - 1][random.randint(0, 3) - 1][":index"] = 0


def setdisplay():
	for y in range(0, place_num[0]):
                for x in range(0, place_num[1]):
			if  world[y][x][":index"] != "":
				world[y][x][":display"] = world[y][x][":index"]  
def countfreeplaces(key, value):
	counter = 0
	for y in range(0, place_num[0]):
                for x in range(0, place_num[1]):
			if  world[y][x][key] == value:
        			counter += 1                     
	return counter 	


def presentworld():
	for y in range(0, place_num[0]):
        	for x in range(0, place_num[1]):
			print world[y][x][":display"],
		print ""

def searchplace(xkey, ykey, xval, yval):
	for reihe in world:
		for place in reihe:
			if place[xkey] == xval and place[ykey] == yval:
				  return place 
def setindex(coords, value): 
	world[coords[1]][coords[0]][":index"] = value
	
def searchinworld(place):
	for y in range(0, place_num[0]):
		for x in range(0, place_num[1]):
			if place == world[y][x]:
				return x, y	

def searchindex(value):
	for reihe in world:
                for place in reihe:
                        if place[":index"] == value:
                                  return place    



def numbering(origins, layer):
	recurcoords = []
	for origin in origins:
		x = origin[0]
		y = origin[1]
		for i in [1, -1]:
			coords = searchinworld(searchplace(":x", ":y", x + i, y))
			if coords != None and searchplace(":x", ":y", x + i, y)[":index"] ==  "":
				setindex(coords, layer)
				recurcoords.append(coords)		
		for i in [1, -1]:
			coords = searchinworld(searchplace(":x", ":y", x, y + i))
			if coords != None and searchplace(":x", ":y", x, y + i)[":index"] ==  "":
				setindex(coords, layer)	
				recurcoords.append(coords)	
	setdisplay()
	#print countfreeplaces(":index", "")
	if countfreeplaces(":index", "") > 0:
		#print "*****************"	
		#presentworld()
		numbering(recurcoords, layer + 1) 
	

#presentworld()

numbering([searchinworld(searchindex(0))], 1)

print "*****************"
presentworld()








