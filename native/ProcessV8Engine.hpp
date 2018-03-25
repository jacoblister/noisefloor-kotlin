#include "Process.hpp"

#include "include/libplatform/libplatform.h"
#include "include/v8.h"

#include <atomic>
#include <mutex>

class ProcessV8Engine : public Process {
  public:
    ProcessV8Engine() : query_flag(0) {}
    virtual void init(void);
    virtual void start(int sampling_rate, int samples_per_frame);
    virtual float *process(float *samples);
    virtual void stop(void);
    virtual std::string query(std::string endpoint, std::string request);
  private:
    void compile(std::string filename);
    void compile_source(std::string filename);

    int samples_per_frame;
    v8::Isolate::CreateParams create_params;
    v8::Isolate* isolate;
    v8::Eternal<v8::Context> context;
    v8::Eternal<v8::Function> process_function;

    // todo - do a lot better
    std::atomic<bool> query_flag;
    std::mutex        query_mutex;
    std::string       query_endpoint;
    std::string       query_request;
    std::string       query_response;
};
