import * as React from 'react';
import PropTypes from 'prop-types';
import {Message} from 'semantic-ui-react'

class ErrorLabel extends React.Component {
    render() {
        if (this.props.value) {
            return (
                <Message
                    negative
                    header={this.props.value.message}
                />
            );
        }
        return null
    }
}

ErrorLabel.propTypes = {
  value: PropTypes.object,
};

export default ErrorLabel;