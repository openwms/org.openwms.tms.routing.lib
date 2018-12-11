import * as React from 'react'
import {connect} from 'react-redux'
import PropTypes from 'prop-types'
import {ACTIONS} from '@ameba/ameba-js'
import {Message} from 'semantic-ui-react'

class SuccessLabel extends React.Component {

    componentDidMount() {
        setTimeout(() => {
            this.props.dismiss();
        }, 3000)
    }

    render() {
        if (this.props.value) {
            return (
                <Message
                    positive
                    header={this.props.value.message}
                />
            );
        }
        return null
    }
};

SuccessLabel.propTypes = {
    value: PropTypes.object,
};

function mapStateToProps(state, ownProps) {
    return {
        value: state.success
    }
}

function mapDispatchToProps(dispatch) {
    return {
        dismiss: () => {
            dispatch({
                type: ACTIONS.RESET_SUCCESS_MESSAGE,
            })
        },
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SuccessLabel);