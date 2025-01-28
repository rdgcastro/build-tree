import csv

with open("CONJUNTOS.csv", newline="") as file:
    file = csv.reader(file, delimiter=",", quotechar='"')
    refs = [tuple(x) for x in file]

tree = [
    ("A", "B"),
    ("B", "C"),
    ("A", "D"),
    ("D", "E"),
    ("D", "F"),
    ("F", "G"),
    ("G", "H"),
    ("H", "I"),
    ("I", "J"),
    ("B", "2"),
]

stack = [("00130160", [])]
root = []
while stack:
    # key: value to search, value: your path in tree
    key, value = stack.pop()

    search = []
    for i, p in refs:  # loop in base (parent, child)
        if i == key:
            if not value:  # it's need to set root value
                search.append((p, [key, p]))
            else:
                val = value.copy()  # copy value path
                val.append(p)  # add key
                search.append((p, val))  # add to list of the founded values

    if search:
        for x in search:
            # make path of the tree for current item
            path = "-".join(v for v in x[1])
            # set current item and path
            root.append([x[0], path])
        # add to stack founded items
        stack += search

# sort list by path key, this set correct items sequence
root.sort(key=lambda r: (r[1]))
for x in root:
    print(x)
