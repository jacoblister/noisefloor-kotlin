ifeq ($(OS),Windows_NT)
    # Windows
    CPP=cl
    CFLAGS=/EHsc
    O=obj
else
    # Linux
    CPP=g++
    V8_DIR=/home/jacob/v8
    CFLAGS=-I$(V8_DIR) -I$(V8_DIR)/include -std=c++0x
    O=o

    LINKFLAGS=-Wl,--start-group                                                     \
        $(V8_DIR)/out.gn/x64.release/obj/libv8_base.a                               \
        $(V8_DIR)/out.gn/x64.release/obj/libv8_libbase.a                            \
        $(V8_DIR)/out.gn/x64.release/obj/libv8_external_snapshot.a                  \
        $(V8_DIR)/out.gn/x64.release/obj/libv8_libplatform.a                        \
        $(V8_DIR)/out.gn/x64.release/obj/libv8_libsampler.a                         \
        $(V8_DIR)/out.gn/x64.release/obj/third_party/icu/libicuuc.a                 \
        $(V8_DIR)/out.gn/x64.release/obj/third_party/icu/libicui18n.a               \
        -Wl,--end-group -lrt -ldl -pthread -std=c++0x -lc++ -ljack

    LINKEXTRA=-o NoiseFloor
endif
LINK=$(CPP)

%.o: %.cpp
	$(CPP) -c -O2 -o $@ $< $(CFLAGS)

%.obj: %.cpp
	$(CPP) -c -O2 $< $(CFLAGS)

all: NoiseFloor

NoiseFloor.$O: NoiseFloor.cpp

ProcessMock.$O: ProcessMock.cpp ProcessMock.hpp
ProcessV8Engine.$O: ProcessV8Engine.cpp ProcessV8Engine.hpp

DriverMock.$O: DriverMock.cpp DriverMock.hpp
DriverJack.$O: DriverJack.cpp DriverJack.hpp

ClientMock.$O: ClientMock.cpp ClientMock.hpp
ClientRESTServer.$O: ClientRESTServer.cpp ClientRESTServer.hpp

DRIVERS = DriverMock.$O DriverJack.$O
CLIENTS = ClientMock.$O ClientRESTServer.$O
PROCESS = ProcessMock.$O ProcessV8Engine.$O
ALL = NoiseFloor.$O $(PROCESS) $(DRIVERS) $(CLIENTS)

NoiseFloor: $(ALL)
	$(LINK) $(ALL) $(LINKFLAGS) $(LINKEXTRA)

clean:
	rm -f *.o *.obj NoiseFloor