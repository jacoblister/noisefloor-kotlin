#include "DriverJack.hpp"

#include <iostream>
#include <string.h>

int process_jack(jack_nframes_t nframes, void *arg) {
    DriverJack *driver = (DriverJack *)arg;

    static bool is_init = 0;
    if (!is_init) {
        std::cout << "init jack thread: nframes=" << nframes << std::endl;
        driver->getProcess().init();
        driver->getProcess().start(jack_get_sample_rate(driver->getJackClient()), nframes);
        is_init = 1;
    }

	jack_default_audio_sample_t *in0, *in1, *out0, *out1;

	in0  = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackInputPort(0),  nframes);
	in1  = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackInputPort(1),  nframes);
	out0 = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackOutputPort(0), nframes);
	out1 = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackOutputPort(1), nframes);

    std::vector<float *> samplesIn =  { in0, in1 };
    std::vector<float *> samplesOut = { out0, out1 };
    driver->getProcess().process(samplesIn, samplesOut);

	return 0;
}

void DriverJack::init() {
}

void DriverJack::start() {
	const char **ports;
	const char *client_name = "noisefloor";
	const char *server_name = NULL;
	jack_options_t options = JackNullOption;
	jack_status_t status;

	/* open a client connection to the JACK server */
    this->jack_client         = jack_client_open(client_name, options, &status, server_name);
	this->jack_input_port[0]  = jack_port_register(jack_client, "input_0",  JACK_DEFAULT_AUDIO_TYPE, JackPortIsInput, 0);
	this->jack_input_port[1]  = jack_port_register(jack_client, "input_1",  JACK_DEFAULT_AUDIO_TYPE, JackPortIsInput, 0);
	this->jack_output_port[0] = jack_port_register(jack_client, "output_0", JACK_DEFAULT_AUDIO_TYPE, JackPortIsOutput, 0);
	this->jack_output_port[1] = jack_port_register(jack_client, "output_1", JACK_DEFAULT_AUDIO_TYPE, JackPortIsOutput, 0);

    jack_set_process_callback(jack_client, process_jack, this);
	jack_activate(jack_client);
}