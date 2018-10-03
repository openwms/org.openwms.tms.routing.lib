import {combineReducers} from 'redux'
//import {ACTIONS} from '@ameba/ameba-js'
import * as pages from '../constants/LinkPages'
import {routerReducer} from "react-router-redux";

function routes(state = [], action) {
    switch (action.type) {
        default:
            return [
                { name: pages.ROUTES_HOME.name, ref: pages.ROUTES_HOME.ref, exact: true },
                { name: pages.ACTIONS_HOME.name, ref: pages.ACTIONS_HOME.ref, exact: true },
            ]
    }
}

const reducers = combineReducers({
    routes,
    router: routerReducer,
});

export default reducers