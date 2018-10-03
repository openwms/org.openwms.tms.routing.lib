import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';
import reducers from './reducers';
import history from './history';
import { connectRouter, routerMiddleware } from 'connected-react-router'


const store = createStore(
    connectRouter(history)(reducers),
    compose(
        applyMiddleware(
            routerMiddleware(history), // for dispatching history actions
            thunk,// ... other middlewares ...
        ),
    ),
);

export default store;