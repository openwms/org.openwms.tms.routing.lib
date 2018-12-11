import React from 'react';
import {connect} from 'react-redux'
import {Route} from "react-router"
import {ConnectedSwitch} from "@ameba/ameba-js"
import RouteList from "./RouteList"
import * as pages from '../../constants/LinkPages'
import * as types from '../../constants/ActionTypes'
import {
    deleteRoute,
    loadLocationGroups,
    loadRoutes,
    openEditRoute,
    saveRoute
} from "../../actions";

class RouteManagement extends React.Component {

    componentDidMount(prevProps, prevState, prevContext) {
        this.props.onLoad();
    }

    render() {
        return (
                <ConnectedSwitch>
                    <Route
                        path={pages.ROUTES_HOME.ref}
                        exact={false}
                        children={({ match }) => (
                            <RouteList
                                match={match}
                                routes={this.props.routes}
                                onCreate={this.props.onCreateRoute}
                                onDelete={this.props.onDeleteRoute}
                                onModify={this.props.onModifyRoute}
                                onChangeStatus={this.props.onRouteStatusChange}
                            />
                        )} />
            </ConnectedSwitch>
        )
    }
}

const mapStateToProps = (state, props) => (
    {
        routes: state.routes,
        locationGroups: state.locationGroups,
    }
);

const mapDispatchToProps = (dispatch) => (
    {
        onLoad: () => {
            dispatch(loadLocationGroups());
            dispatch(loadRoutes())
        },
        onCreateRoute: () => {
            dispatch({
                type: types.ROUTE_DIALOG_OPEN,
                route: {}
            })
        },
        onDeleteRoute: (route) => {
            dispatch(deleteRoute(route))
        },
        onModifyRoute: (route) => {
            dispatch(openEditRoute(route));
        },
        onRouteStatusChange: (route) => {
            dispatch(saveRoute(route));
        },
    }
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(RouteManagement)
