import React from 'react';
import {Provider} from 'react-redux';
import {ConnectedRouter} from "connected-react-router";

import history from './history';
import {HashRouter} from "react-router-dom";
import Start from "./components/Start";

export default class Root extends React.Component {

    state = {store: null, globalEventDistributor: null};

    componentDidCatch(error, info) {
        console.log(error, info);
    }

    setStore(store) {
        this.setState({...this.state, store: store});
    }

    setGlobalEventDistributor(globalEventDistributor) {
        this.setState({...this.state, globalEventDistributor: globalEventDistributor});
    }

    render() {
        let ret = <div>Something went essentially wrong</div>;
        if (this.state.store) {
            ret =
                <Provider store={this.state.store}>
                    <ConnectedRouter history={history}>
                        <HashRouter basename='/'>
                            <Start />
                        </HashRouter>
                    </ConnectedRouter>
                </Provider>
        }
        return ret;
    }
}
