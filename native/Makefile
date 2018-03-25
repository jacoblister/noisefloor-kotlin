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

%.o: %.cpp
	$(CPP) -c -O2 -o $@ $< $(CFLAGS)

all: NoiseFloor

NoiseFloor.o: NoiseFloor.cpp
ProcessV8Engine.o: ProcessV8Engine.cpp ProcessV8Engine.hpp
DriverJack.o: DriverJack.cpp DriverJack.hpp
ApiRESTServer.o: ApiRESTServer.cpp ApiRESTServer.hpp

ALLOBJECTS = NoiseFloor.o ProcessV8Engine.o DriverJack.o ApiRESTServer.o

NoiseFloor: $(ALLOBJECTS)
	$(LINK) $(ALLOBJECTS) -o NoiseFloor $(LINKFLAGS)

clean:
	rm -f *.o NoiseFloor