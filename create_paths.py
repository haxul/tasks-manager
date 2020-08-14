#!/usr/bin/python3.8

# script to create new dirs

import sys
import os


def stop_script(message):
    print(message)
    sys.exit(1)


args = sys.argv[1:]

path = None,
dirs = None
for i in args:
    if "path" in i:
        path = i.split("=")[1]
        if path[-1] == "/":
            stop_script("<<<incorrect path>>>")
        if path == "":
            stop_script("<<<path is empty>>>")
    if "dirs" in i:
        dirs = i.split("=")[1].split(",")
        if len(dirs) == 0:
            stop_script("<<<dirs is empty>>>")


if not path or not dirs:
    stop_script("<<<path or dirs equal None>>>")

for dir_elem in dirs:
    new_dir = path + "/" + dir_elem
    if not os.path.exists(new_dir):
        os.makedirs(new_dir)
        print("create new dir in " + new_dir)

