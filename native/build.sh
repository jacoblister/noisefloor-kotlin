#!/bin/sh
V8_DIR=/home/jacob/v8

## Compile
#g++ -I$V8_DIR -I$V8_DIR/include -c hello-world.cc -o hello-world.o -std=c++0x
#
## Link
#g++ hello-world.o -o hello-world -Wl,--start-group                              \
#    $V8_DIR/out.gn/x64.release/obj/libv8_base.a                                 \
#    $V8_DIR/out.gn/x64.release/obj/libv8_libbase.a                              \
#    $V8_DIR/out.gn/x64.release/obj/libv8_external_snapshot.a                    \
#    $V8_DIR/out.gn/x64.release/obj/libv8_libplatform.a                          \
#    $V8_DIR/out.gn/x64.release/obj/libv8_libsampler.a                           \
#    $V8_DIR/out.gn/x64.release/obj/third_party/icu/libicuuc.a                   \
#    $V8_DIR/out.gn/x64.release/obj/third_party/icu/libicui18n.a                 \
#-Wl,--end-group -lrt -ldl -pthread -std=c++0x -lc++

# Compile
g++ -I$V8_DIR -I$V8_DIR/include -O2 -c jack-client.cc -o jack-client.o x

# Link
g++ jack-client.o -o jack-client -Wl,--start-group                              \
    $V8_DIR/out.gn/x64.release/obj/libv8_base.a                                 \
    $V8_DIR/out.gn/x64.release/obj/libv8_libbase.a                              \
    $V8_DIR/out.gn/x64.release/obj/libv8_external_snapshot.a                    \
    $V8_DIR/out.gn/x64.release/obj/libv8_libplatform.a                          \
    $V8_DIR/out.gn/x64.release/obj/libv8_libsampler.a                           \
    $V8_DIR/out.gn/x64.release/obj/third_party/icu/libicuuc.a                   \
    $V8_DIR/out.gn/x64.release/obj/third_party/icu/libicui18n.a                 \
-Wl,--end-group -lrt -ldl -pthread -std=c++0x -lc++ -ljack
