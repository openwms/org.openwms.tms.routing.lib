import {combineReducers} from 'redux'
import * as pages from '../constants/LinkPages'
import * as types from '../constants/ActionTypes'
import {connectRouter} from 'connected-react-router'
import {ACTIONS} from "@ameba/ameba-js";

function clientroutes(state = [], action) {
    switch (action.type) {
        default:
            return [
                { name: pages.ROUTES_HOME.name, ref: pages.ROUTES_HOME.ref, exact: true },
                { name: pages.ACTIONS_HOME.name, ref: pages.ACTIONS_HOME.ref, exact: true },
            ]
    }
}

function error(state = null, action) {
    switch (action.type) {
        case ACTIONS.SET_ERROR:
            return action.error;
        case ACTIONS.RESET_ERROR:
        case ACTIONS.RESET_SUCCESS_MESSAGE:
            return null;
        default:
            return state
    }
}

function locationGroups(state = [], action) {
    switch (action.type) {
        case types.UPDATE_LOCATION_GROUPS:
            return action.locationGroups;
        default:
            return state
    }
}

function routes(state = [], action) {
    switch (action.type) {
        case types.UPDATE_ROUTES:
            return action.routes;
        default:
            return state
    }
}

function route(state = {}, action) {
    switch (action.type) {
        case types.ROUTE_DIALOG_OPEN:
        case types.UPDATE_ROUTE:
            return action.route;
        default:
            return {}
    }
}
function createRouteDialogOpen(state = false, action) {
    switch (action.type) {
        case types.ROUTE_DIALOG_OPEN:
            return true;
        case types.ROUTE_DIALOG_CLOSE:
            return false;
        default:
            return state
    }
}




function actions(state = [], action) {
    switch (action.type) {
        case types.UPDATE_ACTIONS:
            return action.actions;
        default:
            return state
    }
}

function action(state = {name:'', type:''}, action) {
    switch (action.type) {
        case types.ACTION_DIALOG_OPEN:
        case types.UPDATE_ACTION:
            return action.action;
        default:
            return state
    }
}
function createActionDialogOpen(state = false, action) {
    switch (action.type) {
        case types.ACTION_DIALOG_OPEN:
            return true;
        case types.ACTION_DIALOG_CLOSE:
            return false;
        default:
            return state
    }
}
/*
const reducers = combineReducers({
    router: routerReducer,
});
*/
const reducers = (history) => combineReducers({
    clientroutes,
    error,
    locationGroups: locationGroups,
    routes: routes,
    route: route,
    createRouteDialogOpen: createRouteDialogOpen,
    actions: actions,
    action: action,
    createActionDialogOpen: createActionDialogOpen,
    router: connectRouter(history)
});
export default reducers