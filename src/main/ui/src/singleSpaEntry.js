import React from 'react';
import ReactDOM from 'react-dom';
import singleSpaReact from 'single-spa-react';
import Root from './root.component.js';
import store from "./store";

const reactLifecycles = singleSpaReact({
	React,
	ReactDOM,
	rootComponent: Root,
    domElementGetter,
});

export function bootstrap(props) {
	return reactLifecycles.bootstrap(props);
}

export function mount(props) {
	return reactLifecycles.mount(props).then((rootComponent) => {
		if (props.customProps.store) {
            console.log('Getting a store from portal');
        	rootComponent.setStore(props.customProps.store);
		} else {
			console.error('No managed store instance provided from portal, creating an own one.');
            rootComponent.setStore(store);
		}
		if (props.customProps.globalEventDistributor) {
			console.log('Getting a globalEventDistributor from portal');
	        rootComponent.setGlobalEventDistributor(props.customProps.globalEventDistributor);
		}
    });
}

export function unmount(props) {
	return reactLifecycles.unmount(props);
}

function domElementGetter() {
	let el = document.getElementById('root');
	if (!el) {
		el = document.createElement('div');
		el.id = 'root';
		document.body.appendChild(el);
	}
	return el;
}
