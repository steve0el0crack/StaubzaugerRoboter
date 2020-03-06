import random

"""
BASIC STRUCTURE

	[[{ :estate	0/1	----> Sucio / Limpio
	   :distance	x 	----> Movements needed to be there, from origin
	   :origin	0/1 }	----> Staubzaber oder nicht	
	  {	...	}
			.
			.
			. ]	
	 [	...	  ]	----> Eine Reihe
			.
			.
			.   ]	----> Die Welt			
""" 

#Function framework
def present(x):
	print x
	return x
def add(array, element):
	array.append(element) 

#PARTIAL:	fn ------> fn(x, fest)
def partial(fn, fest, array):
	map(lambda x: fn(x, fest), array )


place_num = [3, 3]

#INITIALLIZING WORLD
# ----> [{":state" : ""}]
world = []
for reihe in range(0, place_num[0]):
	world.append([])
	for place in range(0, place_num[1]):
		world[reihe].append({":state":""}) 
def render(array, change):
	print ".............             " + change
	for x in array:
		print str(x)
	print "-------------" 
render(world, "INITIALIZING WORLD")

#SETTING ORIGIN AND STATES
origin = [random.randint(0,len(world) - 1), random.randint(0,len(world[0]) - 1)]
def def_key_value(dicc, key, value):
	dicc[key] = value
map(lambda reihe: map(lambda place: def_key_value(place, ":state", 0), reihe), world)
world[origin[0]][origin[1]][":origin"] = 1
render(world, "SETTING ORIGIN AND STATES")

#Apllying movement logic

relplaces = []
relindex = []

#To reach EACH place / index
def applytoeachplace(fn):
	map(lambda reihe: map(lambda place: fn(place), reihe), world)
def applytoeachindex(fn):
	map(lambda reihe: map(lambda place: fn({"x":place, "y":reihe}), range(len(world[reihe]))), range(len(world)))

#applytoeachindex(lambda x: present(x))
#applytoeachplace(lambda x: present(x))

#partial(add, relindex, [applytoeachindex(lambda x: present(x))])
#partial(add, relplaces, [applytoeachplace(lambda x: present(x))])

#I would like to have {place}:{index}, but instead will have relplaces =[] and relindex = []

#map(present, [relplaces, relindex])

#SETTING DISTANCES
def setdistance(place):
	place[":distance"] = "DISTANCE TEST"
applytoeachplace(lambda x: setdistance(x))
render(world, "SETTING DISTANCES")


#CRITERIAs
def isorigin(place):
	if ":origin" in place.keys():
		present(place)	#To RENDER
		return True

def isthesame(p1, p2):
	if p1 == p2: 
		present("the same")
		return True

#Get a place with respect to a CRITERIA
def getplace(criteria):
	applytoeachplace(lambda x: filter(criteria, [x]))

def getindex(place):
	applytoeachplace(lambda x: partial(isthesame, place, [x]))

getplace(isorigin)
getindex(getplace(isorigin))

#present(getplace(isorigin))

#def obtainindex(place)
	
#render(world, "XXXXXXXX")



tmpworld = []                                                   #ROH WORLD
map(lambda reihe: map(lambda column: map(lambda pair: tmpworld.append(pair),[{":y" : reihe, ":x" : column}]), range(10)), range(10))
print tmpworld

