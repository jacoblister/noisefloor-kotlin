CPP=g++
V8_DIR=/home/jacob/v8
CFLAGS=-I$(V8_DIR) -I$(V8_DIR)/include -std=c++0x
LINK=g++
LINKFLAGS=-Wl,--start-group                                                     \
    $(V8_DIR)/out.gn/x64.release/obj/libv8_base.a                               \
    $(V8_DIR)/out.gn/x64.release/obj/libv8_libbase.a                            \
    $(V8_DIR)/out.gn/x64.release/obj/libv8_external_snapshot.a                  \
    $(V8_DIR)/out.gn/x64.release/obj/libv8_libplatform.a                        \
    $(V8_DIR)/out.gn/x64.release/obj/libv8_libsampler.a                         \
    $(V8_DIR)/out.gn/x64.release/obj/third_party/icu/libicuuc.a                 \
    $(V8_DIR)/out.gn/x64.release/obj/third_party/icu/libicui18n.a               \
-Wl,--end-group -lrt -ldl -pthread -std=c++0x -lc++ -ljack

%.o: %.cc
	$(CPP) -c -O2 -o $@ $< $(CFLAGS)

all: hello-world jack-client jack-client-blocking alsa-client

hello-world: hello-world.o
	$(LINK) hello-world.o -o hello-world $(LINKFLAGS)

jack-client: jack-client.o
	$(LINK) jack-client.o -o jack-client $(LINKFLAGS)

clean:
	rm -f *.o hello-world jack-client alsa-client