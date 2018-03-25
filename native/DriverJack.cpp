#include "DriverJack.hpp"

#include <string.h>

int process_jack(jack_nframes_t nframes, void *arg) {
    DriverJack *driver = (DriverJack *)arg;

    static bool is_init = 0;
    if (!is_init) {
        printf("init jack thread\n");
        driver->getProcess().init();
        driver->getProcess().start(jack_get_sample_rate(driver->getJackClient()), nframes);
        is_init = 1;
    }

	jack_default_audio_sample_t *in, *out;

	in  = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackInputPort(),  nframes);
	out = (jack_default_audio_sample_t *)jack_port_get_buffer(driver->getJackOutputPort(), nframes);

    driver->getProcess().process(in);

	memcpy(out, in, sizeof (jack_default_audio_sample_t) *nframes);

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
    this->jack_client = jack_client_open(client_name, options, &status, server_name);
	this->jack_input_port  = jack_port_register (jack_client, "input",
                                                 JACK_DEFAULT_AUDIO_TYPE,
                                                 JackPortIsInput, 0);
	this->jack_output_port = jack_port_register (jack_client, "output",
                                                 JACK_DEFAULT_AUDIO_TYPE,
                                                 JackPortIsOutput, 0);
    jack_set_process_callback(jack_client, process_jack, this);
	jack_activate(jack_client);
}