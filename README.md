
# Primordial Soups

A mod for minecraft 1.20.1

# Current Content

Iron Soup and Stock fluids are implemented.
Iron Soup dries into iron ore (takes about a minute on average)
Stock will be a crafting ingredient for all ore soups once the crock pots are implemented.

# Planned Content

Randomly generated ore lodes that look kind of like the deposits from Satisfactory and have some special ‘primordial crock’ blocks on them. You can put a campfire under one and right click it with crock polish to ‘activate’ it. To use an activated crock, fill it with water and insert (hopper, pipe, or whatever) in ingredients to make the primordial soup! Each crock type can make a specific type of primordial soup. To make use of the soup, just place it down in the world with a bucket and it will dry up into a block of ore. This happens when the liquid receives a random tick, which happens on average every 68 seconds.

Primordial crock pots spawn around the world for all metal ore types and some other materials. Each type of crock pot produces a different resource, which needs 4 ingredients and liquid stock. Use crock polish on a crock pot to activate it. Activated crock pots have a four-slot internal inventory, an input tank, and an output tank. When a dispenser uses an empty bucket on the crock, it will try to drain fluid from the output tank first and if it is empty it will drain the input. When a dispenser uses a filled bucket on the crock, it will insert the fluid into the input tank. There is no way to insert into the output tank. Crocks can accept items and liquids from any side. Comparator outputs the fullness of the output tank: 0 for empty, 15 for completely full, rounding down.
