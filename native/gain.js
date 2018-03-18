function g(samples) {
    for(var i = samples.length; i--;) { samples[i] *= 10; }
}

//function start(sampleRate) { console.log = function (message) { console_log(message) }; console.log('called start'); }
function start(sampleRate) { console.log('called start'); }

function process(samples) { console.log('gotme'); g(samples); return samples; }
