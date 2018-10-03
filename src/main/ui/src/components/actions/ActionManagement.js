import React from 'react';
import {connect} from 'react-redux'
import {Route} from "react-router";
import * as pages from "../../constants/LinkPages";
import {ConnectedSwitch} from "@ameba/ameba-js"

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

class ActionManagement extends React.Component {

    componentDidMount(prevProps, prevState, prevContext) {
        this.props.onLoadActions();
    }

    render() {
        return (
            <ConnectedSwitch>
                <Route
                    path={pages.ACTIONS_HOME.ref}
                    exact={true}
                    children={({ match }) => (
                        <div></div>
                    )} />
            </ConnectedSwitch>
        )
    }
}

const mapStateToProps = (state, props) => (
    {
        actions: state.actions,
    }
);

const mapDispatchToProps = (dispatch) => (
    {
        onLoadActions: () => {
        },
    }
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ActionManagement)
