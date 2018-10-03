import React from 'react';
import {connect} from 'react-redux'
import {Route} from "react-router"
import {ConnectedSwitch} from "@ameba/ameba-js"
import RouteList from "./RouteList"
import * as pages from '../../constants/LinkPages'
import {loadRoutes} from "../../actions";

const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit * 3,
    }),
    button: {
        marginLeft: theme.spacing.unit * 3,
    },
});

class RouteManagement extends React.Component {

    componentDidMount(prevProps, prevState, prevContext) {
        this.props.onLoadRoutes();
    }

    render() {
        return (
            <ConnectedSwitch>
                <Route
                    path={pages.ROUTES_HOME.ref}
                    exact={true}
                    children={({ match }) => (
                        <RouteList
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
        error: state.error,
        success: state.success,
        routes: state.routes,
    }
);

const mapDispatchToProps = (dispatch) => (
    {
        onLoadRoutes: () => {
            dispatch(loadRoutes())
        },
        onCreateRoute: () => {
        },
        onDeleteRoute: () => {
        },
        onModifyRoute: () => {
            console.log('on modify')
        },
        onRouteStatusChange: () => {
        },
    }
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(RouteManagement)
