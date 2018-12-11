import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';
import reducers from './reducers';
import history from './history';
import {routerMiddleware} from 'connected-react-router'

const composeEnhancer = compose
const store = createStore(
    reducers(history),
    composeEnhancer(
        applyMiddleware(
            routerMiddleware(history), // for dispatching history actions
            thunk,// ... other middlewares ...
        ),
    ),
);

export default store;