# !/usr/bin/python

SPRITE_FOLDER = "./raw/sprites/body/"
OUTPUT_FOLDER = "./raw/sprites/32x32/body/"

import os

# os.system('magick "./assets/raw/body/02sock/fbas_02sock_sockshigh_00a.png" -crop 64x64 "./assets/raw/sprites/body/02sock/fbas_02sock_sockshigh_00a_%02d.png"')


for root, dirs, files in os.walk(SPRITE_FOLDER, topdown=False):
    for name in files:

        DIR = root.replace("/body/", "/32x32/body/")

        if not os.path.exists(DIR):
            os.makedirs(DIR)

        cmd = 'magick "' + root + "/" + name + '" -crop 32x32 "' + DIR + "/" + name.replace(".png", "") + '_%02d.png"'
        print(cmd)
        os.system(cmd)
