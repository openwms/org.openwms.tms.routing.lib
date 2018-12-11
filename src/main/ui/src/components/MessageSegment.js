import * as React from 'react';
import PropTypes from 'prop-types';
import {Segment} from 'semantic-ui-react'
import ErrorLabel from "./ErrorLabel";
import SuccessLabel from "./SuccessLabel";

class MessageSegment extends React.Component {
    render() {
        if (this.props.error || this.props.success) {
            return (
                <Segment padded>
                    <ErrorLabel value={this.props.error} />
                    <SuccessLabel value={this.props.success} />
                </Segment>
            )
        }
        return null;
    }
}

MessageSegment.propTypes = {
    active: PropTypes.bool,
    error: PropTypes.object,
    success: PropTypes.object,
};

export default MessageSegment;