#-------------------------------------------------------------------------------
#
#   SGCraft - Makefile
#
#-------------------------------------------------------------------------------

MODNAME := SGCraft
MODTITLE := SG Craft
MODSUBPKG := sg

MAJVER := 0
MINVER := 7
BUGVER := 1

JARCONTENTS = \
    -C $(1) $(MODPKG) \
    -C $(1) net/minecraft/world/gen/structure/MapGenStructureAccess.class \
    -C src/mod assets

include ../Mod.make
