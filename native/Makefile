ifeq ($(OS),Windows_NT)
    # Windows
    TARGET_PLATFORM=WINDOWS
    TARGET_AUDIO=AUDIO_ASIO
    TARGET_PROCESS=PROCESS_MOCK
    TARGET_CLIENT=CLIENT_MOCK

    CPP=cl
    CFLAGS=/EHsc
    O=obj

    LINKEXTRA=User32.lib Advapi32.lib Ole32.lib
else
    # Linux
    TARGET_PLATFORM=LINUX
    TARGET_AUDIO=AUDIO_JACK
    TARGET_PROCESS=PROCESS_V8
    TARGET_CLIENT=CLIENT_MOCK

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
DEFINES=-D$(TARGET_PLATFORM) -D$(TARGET_AUDIO) -D$(TARGET_PROCESS) -D$(TARGET_CLIENT)

%.o: %.cpp
	$(CPP) $(DEFINES) -c -O2 -o $@ $< $(CFLAGS)

%.obj: %.cpp
	$(CPP) $(DEFINES) -c -O2 $< $(CFLAGS)

all: NoiseFloor

ASIO_OBJ = asio.obj asiolist.obj asiodrivers.obj
asio.obj: asio/asio.cpp
	cl -c -DWIN32 asio/asio.cpp

asiodrivers.obj: asio/asiodrivers.cpp
	cl -c -DWIN32 asio/asiodrivers.cpp

asiolist.obj: asio/asiolist.cpp
	cl -c -DWIN32 asio/asiolist.cpp

NoiseFloor.$O: NoiseFloor.cpp

ProcessMock.$O: ProcessMock.cpp ProcessMock.hpp
ProcessV8Engine.$O: ProcessV8Engine.cpp ProcessV8Engine.hpp

DriverMock.$O: DriverMock.cpp DriverMock.hpp
DriverASIO.$O: DriverASIO.cpp DriverASIO.hpp
DriverJack.$O: DriverJack.cpp DriverJack.hpp

ClientMock.$O: ClientMock.cpp ClientMock.hpp
ClientRESTServer.$O: ClientRESTServer.cpp ClientRESTServer.hpp

DRIVERS = DriverMock.$O DriverASIO.$O #DriverJack.$O
CLIENTS = ClientMock.$O #ClientRESTServer.$O
PROCESS = ProcessMock.$O #ProcessV8Engine.$O
ALL = NoiseFloor.$O $(PROCESS) $(DRIVERS) $(CLIENTS) $(ASIO_OBJ)

NoiseFloor: $(ALL)
	$(LINK) $(ALL) $(LINKFLAGS) $(LINKEXTRA)

clean:
	rm -f *.o *.obj NoiseFloor