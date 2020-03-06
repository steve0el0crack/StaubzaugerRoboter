import random


place_num = [3, 3]

world = []
for reihe in range(0, place_num[0]):
        world.append([])
        for place in range(0, place_num[1]):
                world[reihe].append({":x": place, ":y": reihe, ":H" : 0, ":index" : ""})

def presentarray(x):
	for i in x:
		print i

world[random.randint(0, 3) - 1][random.randint(0, 3) - 1][":index"] = 0
presentarray(world)

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

def numbering(origin):
	x = origin[":x"]
	y = origin[":y"]
	for i in [1, -1]:
		coords = searchinworld(searchplace(":x", ":y", x + i, y))
		if coords != None:
			print "CH"
			setindex(coords, 1)		
	for i in [1, -1]:
		coords = searchinworld(searchplace(":x", ":y", x, y + i))
		if coords != None:
			print "CH"
			setindex(coords, 1)	

print "%%%%%%%%%%%%%%%%%"

def searchindex(value):
	for reihe in world:
                for place in reihe:
                        if place[":index"] == value:
                                  return place    
numbering(searchindex(0))

print "---------------------------"

presentarray(world)

#map(lambda x, setindex(x, 1),  numbering(searchplace(":index", 0)))







