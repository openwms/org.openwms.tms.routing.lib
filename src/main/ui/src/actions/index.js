import {ACTIONS} from '@ameba/ameba-js'
import {push} from 'connected-react-router'
import * as SRV from "../services"
import * as types from '../constants/ActionTypes'

export function goto(refs) {
    return (dispatch, getState) => {
        dispatch(push(refs[refs.length-1].ref))
    }
}

export function openEditRoute(route) {
    return (dispatch, getState) => {
        dispatch({
            type: types.ROUTE_DIALOG_OPEN,
            route: route
        });
    }
}

export function loadLocationGroups() {
    return (dispatch, getState) => {
        SRV.loadLocationGroups(
            (locationGroups) => {
                dispatch({
                    type: types.UPDATE_LOCATION_GROUPS,
                    locationGroups: locationGroups
                });
            },
            (error) => dispatch({
                type: ACTIONS.SET_ERROR,
                error: error,
            })
        );
    }
}

export function loadRoutes() {
    return (dispatch, getState) => {
        SRV.loadRoutes(
            (routes) => {
                dispatch({
                    type: types.UPDATE_ROUTES,
                    routes: routes
                });
            },
            (error) => dispatch({
                type: ACTIONS.SET_ERROR,
                error: error,
            })
        );
    }
}
export function saveRoute(route, onSuccess) {
    return (dispatch, getState) => {
        route.key ?
        SRV.saveRoute(
            route,
            () => {
                dispatch({
                    type: types.ROUTE_SAVED,
                    success: {message: 'Route saved'},
                });
                SRV.loadRoutes(
                    (routes) => {
                        dispatch({
                            type: types.UPDATE_ROUTES,
                            routes: routes,
                        });
                        onSuccess()
                    },
                    (error) => dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: error,
                    })
                );
            },
            (e) => {
                dispatch({
                    type: ACTIONS.SET_ERROR,
                    error: e,
                })
            }
        ) :
        SRV.createRoute(
            route,
            () => {
                dispatch({
                    type: types.ROUTE_CREATED,
                    success: {message: 'Route created'},
                });
                SRV.loadRoutes(
                    (routes) => {
                        dispatch({
                            type: types.UPDATE_ROUTES,
                            routes: routes,
                        });
                        onSuccess()
                    },
                    (error) => dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: error,
                    })
                );
            },
            (e) => {
                dispatch({
                    type: ACTIONS.SET_ERROR,
                    error: e,
                })
            }
        );
    }
}
export function deleteRoute(route, onSuccess) {
    return (dispatch, getState) => {
        SRV.deleteRoute(
            route,
            () => {
                dispatch({
                    type: types.ROUTE_DELETED,
                    success: {message: 'Route deleted'},
                });
                SRV.loadRoutes(
                    (routes) => {
                        dispatch({
                            type: types.UPDATE_ROUTES,
                            routes: routes,
                        });
                        onSuccess()
                    },
                    (error) => dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: error,
                    })
                );
            },
            (e) => {
                dispatch({
                    type: ACTIONS.SET_ERROR,
                    error: e,
                })
            }
        );
    }
}






export function openEditAction(action) {
    return (dispatch, getState) => {
        dispatch({
            type: types.ACTION_DIALOG_OPEN,
            action: action
        });
    }
}
export function loadActions() {
    return (dispatch, getState) => {
        SRV.loadActions(
            (actions) => {
                dispatch({
                    type: types.UPDATE_ACTIONS,
                    actions: actions
                });
            },
            (error) => dispatch({
                type: ACTIONS.SET_ERROR,
                error: error,
            })
        );
    }
}
export function saveAction(action, onSuccess) {
    return (dispatch, getState) => {
        action.key ?
            SRV.saveAction(
                action,
                () => {
                    dispatch({
                        type: types.ACTION_SAVED,
                        success: {message: 'Action saved'},
                    });
                    SRV.loadActions(
                        (actions) => {
                            dispatch({
                                type: types.UPDATE_ACTIONS,
                                actions: actions,
                            });
                            onSuccess()
                        },
                        (error) => dispatch({
                            type: ACTIONS.SET_ERROR,
                            error: error,
                        })
                    );
                },
                (e) => {
                    dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: e,
                    })
                }
            ) :
            SRV.createAction(
                action,
                () => {
                    dispatch({
                        type: types.ACTION_CREATED,
                        success: {message: 'Action created'},
                    });
                    SRV.loadActions(
                        (actions) => {
                            dispatch({
                                type: types.UPDATE_ACTIONS,
                                actions: actions,
                            });
                            onSuccess()
                        },
                        (error) => dispatch({
                            type: ACTIONS.SET_ERROR,
                            error: error,
                        })
                    );
                },
                (e) => {
                    dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: e,
                    })
                }
            );
    }
}
export function deleteAction(action, onSuccess) {
    return (dispatch, getState) => {
        SRV.deleteAction(
            action,
            () => {
                dispatch({
                    type: types.ACTION_DELETED,
                    success: {message: 'Action deleted'},
                });
                SRV.loadActions(
                    (actions) => {
                        dispatch({
                            type: types.UPDATE_ACTIONS,
                            actions: actions,
                        });
                        onSuccess()
                    },
                    (error) => dispatch({
                        type: ACTIONS.SET_ERROR,
                        error: error,
                    })
                );
            },
            (e) => {
                dispatch({
                    type: ACTIONS.SET_ERROR,
                    error: e,
                })
            }
        );
    }
}


