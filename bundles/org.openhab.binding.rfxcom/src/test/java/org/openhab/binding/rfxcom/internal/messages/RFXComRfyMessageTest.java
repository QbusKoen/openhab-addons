/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.rfxcom.internal.messages;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.openhab.binding.rfxcom.internal.messages.RFXComBaseMessage.PacketType.RFY;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.binding.rfxcom.internal.exceptions.RFXComException;
import org.openhab.binding.rfxcom.internal.messages.RFXComRfyMessage.Commands;
import org.openhab.binding.rfxcom.internal.messages.RFXComRfyMessage.SubType;
import org.openhab.core.util.HexUtils;

/**
 * Test for RFXCom-binding
 *
 * @author Martin van Wingerden - Initial contribution
 */
@NonNullByDefault
public class RFXComRfyMessageTest {

    @Test
    public void basicBoundaryCheck() throws RFXComException {
        RFXComRfyMessage message = (RFXComRfyMessage) RFXComMessageFactoryImpl.INSTANCE.createMessage(RFY);

        message.subType = SubType.RFY;
        message.command = Commands.UP;

        RFXComTestHelper.basicBoundaryCheck(RFY, message);
    }

    private void testMessage(SubType subType, Commands command, String deviceId, String data) throws RFXComException {
        RFXComRfyMessage message = (RFXComRfyMessage) RFXComMessageFactoryImpl.INSTANCE.createMessage(RFY);
        message.setSubType(subType);
        message.command = command;
        message.setDeviceId(deviceId);

        assertArrayEquals(HexUtils.hexToBytes(data), message.decodeMessage());
    }

    @Test
    public void testMessage1() throws RFXComException {
        testMessage(SubType.RFY, Commands.UP_SHORT, "66051.4", "0C1A0000010203040F00000000");
    }

    @Test
    public void testMessage2() throws RFXComException {
        testMessage(SubType.ASA, Commands.DOWN_LONG, "66051.4", "0C1A0300010203041200000000");
    }
}
