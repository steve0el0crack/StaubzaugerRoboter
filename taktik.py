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
	if type(coords) == None:
                print "XXXXXXXXXX"
		return 	
	else:
		print coords
		world[coords[0]][coords[1]][":index"] = value
	
def searchinworld(place):
	if type(place) == None:
		print "XXXXXXXXXX"
		return 
	else:
		for y in range(0, place_num[0]):
			for x in range(0, place_num[1]):
				if place == world[y][x]:
					return x, y	

def numbering(origin):
	x = origin[":x"]
	y = origin[":y"]
	for i in [1, -1]:
		setindex(searchinworld(searchplace(":x", ":y", x + i, y)), 1)
	for i in [1, -1]:
		setindex(searchinworld(searchplace(":x", ":y", x, y + i)), 1)

print "%%%%%%%%%%%%%%%%%"

def searchindex(value):
	for reihe in world:
                for place in reihe:
                        if place[":index"] == value:
                                  return place    
numbering(searchindex(0))



#map(lambda x, setindex(x, 1),  numbering(searchplace(":index", 0)))







