import {ACTIONS} from '@ameba/ameba-js'
import {push} from 'connected-react-router'
import * as SRV from "../services"
import * as types from '../constants/ActionTypes'

export function goto(refs) {
    return (dispatch, getState) => {
        dispatch(push(refs[refs.length-1].ref))
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

