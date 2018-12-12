import React from 'react';
import {connect} from 'react-redux'
import {Route} from "react-router"
import {ACTIONS, ConnectedSwitch} from "@ameba/ameba-js"
import ActionList from "./ActionList"
import * as pages from '../../constants/LinkPages'
import * as types from '../../constants/ActionTypes'
import {
    deleteAction,
    loadActions,
    loadLocationGroups,
    loadRoutes,
    openEditAction,
    saveAction
} from "../../actions";

class ActionManagement extends React.Component {

    componentDidMount(prevProps, prevState, prevContext) {
        this.props.onLoad();
    }

    render() {
        return (
            <ConnectedSwitch>
                <Route
                    path={pages.ACTIONS_HOME.ref}
                    exact={true}
                    children={({ match }) => (
                        <ActionList
                            match={match}
                            actions={this.props.actions}
                            onCreate={this.props.onCreate}
                            onDelete={this.props.onDelete}
                            onModify={this.props.onModify}
                            onCloseToast={this.props.onCloseToast}
                            error={this.props.error}
                            onChangeStatus={this.props.onStatusChange}
                        />
                    )}
                />
            </ConnectedSwitch>
        )
    }
}

const mapStateToProps = (state, props) => (
    {
        error: state.error,
        actions: state.actions,
        routes: state.routes,
        locationGroups: state.locationGroups,
    }
);

const mapDispatchToProps = (dispatch) => (
    {
        onLoad: () => {
            dispatch(loadLocationGroups());
            dispatch(loadActions());
            dispatch(loadRoutes())
        },
        onCreate: () => {
            dispatch({
                type: types.ACTION_DIALOG_OPEN,
                action: {name:'', type:'', description:'', route:'', program:'', location:'', locationGroupName:'', enabled: true}
            })
        },
        onDelete: (action) => {
            dispatch(deleteAction(action))
        },
        onModify: (action) => {
            dispatch(openEditAction(action));
        },
        onStatusChange: (action) => {
            dispatch(saveAction(action));
        },
        onCloseToast: () => {
            dispatch({
                type: ACTIONS.RESET_ERROR,
            })
        }
    }
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ActionManagement)
