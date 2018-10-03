import {ACTIONS} from '@ameba/ameba-js'
import {push} from 'connected-react-router'

export function goto(refs) {
    return (dispatch, getState) => {
        dispatch(push(refs[refs.length-1].ref))
    }
}
